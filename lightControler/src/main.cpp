#include <iostream>
#include <string>
#include <thread>

#include <unistd.h>
#include <nlohmann/json.hpp>
#include "ControlerSocket.h"
#include "MessageQueue.h"
#include "Logger.h"

using namespace std;

/**
 * The controler for the crossroads' trafic lights.
 * It connects to the central server and wait for patterns to arrive.
 */
int main(int argc, char** argv) {
    std::string serverAddr("127.0.0.1");
    unsigned int serverPort = 5672;
    if (argc > 4 || argc < 2) {
        std::cout << "Usage: " << argv[0] << " identifier [csAddress] [port]" << std::endl;
        std::cout << "identifier:\tAn unique identifier to distinguish this controler from others on the central server." << std::endl;
        std::cout << "csAddress:\tThe address of the central server. Default to " << serverAddr << "." << std::endl;
        std::cout << "port:\tThe port to use to connect to the central server. Default to " << serverPort << "." << std::endl;
        return 0;
    }
    std::string identifier = argv[1];
    if (argc > 2) {
        serverAddr = argv[2];
    }
    if (argc > 3) {
        serverPort = std::atoi(argv[3]);
    }

    Logger log(std::cout, "[" + identifier + "] ");

    std::cout << "Connecting to " << serverAddr << ":" << serverPort << std::endl;
    bool isRunning = true;
    MessageQueue mq(identifier, serverAddr, serverPort, [&](const AMQP::Message& message, uint64_t deliveryTag, bool redelivered) {
        std::string msg(message.body(), message.bodySize());
        std::cout << "Message received " << msg << std::endl;
        if (msg == "stop") {
            std::cout << "Received stop command, switching everything off. Have fun with no trafic lights." << std::endl;
                    isRunning = false;
        }
    });

    // Start a thread to wait for input (walker requesting passage)
    bool wasPassageRequested = false;
    std::thread ioThread([&]() {
        while (std::cin.get() != EOF) {
            wasPassageRequested = true;
        }
    });


    while (isRunning) {
        mq.update();
        // TODO: logic here
        if (wasPassageRequested) {
            mq.notifyPedestrian("N");
            wasPassageRequested = false;
        }
    }
    std::cout << "Bye!" << std::endl;
    return 0;
}

