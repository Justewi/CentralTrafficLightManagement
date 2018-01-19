#include <iostream>
#include <string>
#include <thread>
#include <csignal>
#include <algorithm>
#include <unistd.h>
#include <csignal>
#include "Logger.h"
#include "Controler.h"

#include <chrono>

using namespace std;

bool wasPassageRequested = false;
bool wasPingRequested = false;

void handleWalkerSignal(int signal) {
    wasPassageRequested = true;
}

void handlePingSignal(int signal) {
    wasPingRequested = true;
}

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
    std::cout << "Controler version indicator : 1.0" << std::endl;

    std::cout << "Connecting to " << serverAddr << ":" << serverPort << std::endl;
    Controler ctrl(identifier, serverAddr, serverPort);

    // Wait for a walker to push the button (Through SIGUSR1)
    std::signal(SIGUSR1, handleWalkerSignal);
    std::signal(SIGUSR2, handlePingSignal);


    while (true) {
        ctrl.update();
        if (wasPassageRequested) {
            ctrl.notifyPedestrian(Direction::NS);
            wasPassageRequested = false;
        }
        if (wasPingRequested) {
            ctrl.ping();
            wasPingRequested = false;
        }
    }
    std::cout << "Bye!" << std::endl;
    return 0;
}

