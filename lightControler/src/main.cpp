#include <iostream>
#include <string>

#include "ControlerSocket.h"

using namespace std;

/**
 * The controler for the crossroads' trafic lights.
 * It connects to the central server and wait for patterns to arrive.
 */
int main(int argc, char** argv){
    std::string serverAddr("127.0.0.1");
    unsigned int serverPort = 9278;
    if (argc > 3){
        std::cout << "Usage: " << argv[0] << " [centralServer-address] [port]" << std::endl;
        std::cout << "csAddress:\tThe address of the central server. Default to " << serverAddr << "." << std::endl;
        std::cout << "port:\tThe port to use to connect to the central server. Default to " << serverPort << "." << std::endl;
        return 0;
    }
    if (argc > 1){
        serverAddr = argv[1];
    }
    if (argc > 2){
        serverPort = std::atoi(argv[2]);
    }
    std::cout << "Connecting to " << serverAddr << ":" << serverPort << std::endl;
    ControlerSocket s(serverAddr, serverPort);
    std::cout << "Connected to " << serverAddr << ":" << serverPort << std::endl;

    while (s.isConnected()){
        std::string msg = s.read();
        if (msg == "stop"){
            std::cout << "Received stop command, switching everything off. Have fun with no trafic lights." << std::endl;
            return 0;
        }
        std::cout << msg << std::endl;
        // TODO: logic here
    }

    return 0;
}

