#include "MessageQueue.h"

#include <vector>

MessageQueue::MessageQueue(std::string identifier, std::string serverAddr, unsigned int serverPort, std::function<void(std::string) > onMessage)
: identifier(identifier), mqConnection(&mqHandler, "amqp://" + serverAddr + ":" + std::to_string(serverPort)),
channel(&mqConnection) {
    channel.declareQueue(identifier);
    channel.declareExchange("ctlms_exchanger", AMQP::ExchangeType::direct);
    channel.bindQueue("ctlms_exchanger", identifier, "ALL");
    channel.bindQueue("ctlms_exchanger", identifier, identifier);
    channel.consume(identifier).onSuccess([]() {
        std::cout << "Starting to consume messages." << std::endl;
    }).onMessage([onMessage, this](const AMQP::Message &message, uint64_t deliveryTag, bool redelivered) {
        onMessage(std::string(message.body(), message.bodySize()));
        channel.ack(deliveryTag); // Delete the message from the queue
    }).onError([](const char *message) {
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

