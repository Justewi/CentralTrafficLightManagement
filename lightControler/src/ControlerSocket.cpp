#include "ControlerSocket.h"

#include <unistd.h>
#include <cstring>

ControlerSocket::ControlerSocket(std::string serverAddr, unsigned int port) {
#ifdef __WIN32__
    WSADATA WSAData;
    WSAStartup(MAKEWORD(2, 0), &WSAData);
#endif

    // Perform DNS lookup
    hostent *h = gethostbyname(serverAddr.c_str());
    if (h == NULL)
        throw StupidException("Name resolving failed for (" + serverAddr + ":" + std::to_string(port) + ")", errno);

    if ((fdSocket = socket(AF_INET, SOCK_STREAM, 0)) < 0)
        throw StupidException("Unable to create the socket");
    // Configure the socket
    remoteAddress.sin_family = AF_INET; // IPv4
    memcpy(&remoteAddress.sin_addr, h->h_addr_list[0], h->h_length);
    remoteAddress.sin_port = htons(port); // Port

    // Connect to the given serverAddr
    if (connect(fdSocket, (struct sockaddr*) &remoteAddress, sizeof (remoteAddress)) < 0)
        throw StupidException("Connection failed (<" + serverAddr + ">" + inet_ntoa(remoteAddress.sin_addr) + ":" + std::to_string(port) + ")", errno);
}

ControlerSocket::ControlerSocket(const ControlerSocket& orig) {
}

ControlerSocket::~ControlerSocket() {
#ifdef __linux__
    close(fdSocket);
#elif __WIN32__
    closesocket(fdSocket);
    WSACleanup();
#endif
}

void ControlerSocket::send(std::string msg) {
    if (fdSocket <= 0) throw StupidException("No client connected.", fdSocket);
    unsigned int tmp = htonl(msg.length());
    ::send(fdSocket, (char*) &tmp, sizeof (int), 0);
    ::send(fdSocket, msg.c_str(), msg.length(), 0);
}

/**
 * Get the client address
 */
std::string ControlerSocket::getRemoteAddress() const {
    if (fdSocket <= 0) throw StupidException("No client connected.", fdSocket);
    return std::string(inet_ntoa(remoteAddress.sin_addr));
}

std::string ControlerSocket::read() {
    if (fdSocket <= 0) throw StupidException("No client connected.", fdSocket);
    // Lire la taille du message envoyé
    unsigned int taille;
    int nbRead = ::recv(fdSocket, (char*) &taille, 4, 0);
    if (nbRead <= 0) {
        fdSocket = 0; // TODO: Fix this, we should close the socket (probably?)
        return std::string("");
    }
    taille = ntohl(taille);
    // Lire le message
    char *buf = new char[taille];
    for (unsigned int i = 0; i < taille; i++)
        buf[i] = '\0';
    nbRead = ::recv(fdSocket, buf, taille, 0);

    // Si une erreur est arrivée
    if (nbRead <= 0) {
        delete buf;
        fdSocket = 0;
        return std::string("");
    }

    std::string msg(buf, taille);
    delete[] buf;

    return msg;
}

char ControlerSocket::getChar() {
    if (fdSocket <= 0) throw StupidException("No client connected.", fdSocket);
    char c;
    int nbRead = ::recv(fdSocket, &c, sizeof (char), 0);
    if (nbRead <= 0) {
        fdSocket = 0; // TODO: Fix this, we should close the socket (probably?)
        return 0;
    }
    return c;
}