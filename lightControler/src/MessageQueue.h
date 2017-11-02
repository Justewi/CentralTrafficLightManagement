#ifndef MESSAGEQUEUE_H
#define MESSAGEQUEUE_H

#include <string>

#include <vector>
#include <algorithm>
#include <sys/ioctl.h>
#include <amqpcpp.h>
#include <iostream>

class MessageQueue {

    class ControllerMQHandler : public AMQP::TcpHandler {
    public:
        std::vector<int> fdToMonitor;

        void onConnected(AMQP::TcpConnection* connection) override {
            std::cout << "Connection to MQ successful." << std::endl;
        }

        void onClosed(AMQP::TcpConnection* connection) override {
            std::cout << "Connection to MQ closed." << std::endl;
        }

        void monitor(AMQP::TcpConnection* connection, int fd, int flags) override {
            //TODO: Notify only once, don't monitor forever
            if (std::find(fdToMonitor.begin(), fdToMonitor.end(), fd) == fdToMonitor.end())
                fdToMonitor.push_back(fd);
        }

        void onError(AMQP::TcpConnection* connection, const char* message) override {
            std::cout << "Error with the connection to RabbitMQ: " << message << std::endl;
        }

        uint16_t onNegotiate(AMQP::TcpConnection* connection, uint16_t interval) override {
            std::cout << "Negociating with RabbitMQ" << std::endl;
        }
    };

    std::string identifier;
    ControllerMQHandler mqHandler;
    AMQP::TcpConnection mqConnection;
    AMQP::TcpChannel channel;
public:
    MessageQueue(std::string identifier, std::string serverAddr, unsigned int serverPort, AMQP::MessageCallback onMessage);
    virtual ~MessageQueue();
    void update();

    void notifyPedestrian(std::string direction) {
        channel.publish("ctlms_exchanger", "PEDESTRIAN", "{\"direction\":\"" + direction + "\"}");
        std::cout << "Pedestrian notification sent" << std::endl;
    };
};

#endif /* MESSAGEQUEUE_H */

