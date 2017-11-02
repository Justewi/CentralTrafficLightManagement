#include "MessageQueue.h"

#include <vector>

MessageQueue::MessageQueue(std::string identifier, std::string serverAddr, unsigned int serverPort, AMQP::MessageCallback onMessage)
: identifier(identifier), mqConnection(&mqHandler, "amqp://" + serverAddr + ":" + std::to_string(serverPort)),
channel(&mqConnection) {
    channel.declareQueue(identifier);
    channel.bindQueue("ctlms_exchanger", identifier, "ALL");
    channel.consume(identifier).onSuccess([]() {
        std::cout << "Starting to consume messages." << std::endl;
    }).onMessage(onMessage).onError([](const char *message) {
        std::cout << "Error with the consumer: " << message << std::endl;
    });
}

MessageQueue::~MessageQueue() {
    channel.removeQueue(identifier);
}

void MessageQueue::update() {
    for (auto fd : mqHandler.fdToMonitor) {
        int count = 0;
        ioctl(fd, FIONREAD, &count);
        if (count > 0) {
            mqConnection.process(fd, AMQP::readable | AMQP::writable);
        }
    }
}

