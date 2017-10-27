#ifndef CONTROLERSOCKET_H
#define CONTROLERSOCKET_H

#include <string>
#ifdef __linux__
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <netdb.h>
#elif __WIN32__
#include <ws2tcpip.h>
#endif

class ControlerSocket {
    int fdSocket;
    struct sockaddr_in remoteAddress;
public:
    ControlerSocket(std::string serverAddr, unsigned int port);
    ControlerSocket(const ControlerSocket& orig);
    virtual ~ControlerSocket();
    void send(std::string);
    char getChar(); /// Be aware that this might break the protocol, debug only ;)
    std::string read();
    std::string getRemoteAddress() const;

    bool isConnected() {
        return fdSocket != 0;
    };
};

#endif /* CONTROLERSOCKET_H */

