#ifndef CONTROLER_H
#define CONTROLER_H

#include <string>
#include <chrono>

#include "MessageQueue.h"

enum Direction {
    NS, EW
};

struct Timers {
    std::chrono::seconds nsTime;
    std::chrono::seconds ewTime;
};

class Controler {
    std::string identifier;
    bool isRunning;
    std::chrono::steady_clock::time_point pingStart;
    MessageQueue mq;

    enum Direction cur;
    struct Timers currentPattern;
    struct Timers nextPattern;
    std::chrono::system_clock::time_point nextChange;
    std::chrono::system_clock::time_point nextPatternStart;

public:

    Controler(std::string identifier, std::string serverAddr, unsigned int serverPort);
    Controler(const Controler& orig) = delete;
    virtual ~Controler();
    void update();
    void ping();
    void notifyPedestrian(enum Direction d);
    void handleMessage(std::string msg);
};

#endif /* CONTROLER_H */

