#ifndef CONTROLERSOCKET_H
#define CONTROLERSOCKET_H

#include <string>
#include <ws2tcpip.h>

#include "StupidException.h"

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
};

#endif /* CONTROLERSOCKET_H */

