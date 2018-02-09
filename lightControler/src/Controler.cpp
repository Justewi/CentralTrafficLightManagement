#include "Controler.h"
#include <nlohmann/json.hpp>

Controler::Controler(std::string identifier, std::string serverAddr, unsigned int serverPort)
:
identifier(identifier),
cur(NS),
currentPattern({nsTime : std::chrono::seconds(60), ewTime : std::chrono::seconds(60)}),
nextChange(std::chrono::system_clock::time_point::max()),
mq(identifier, serverAddr, serverPort, std::bind(&Controler::handleMessage, this, std::placeholders::_1)),
nextPatternStart(std::chrono::system_clock::now()){
}

Controler::~Controler() {
}

void Controler::update() {
    mq.update();
    if (std::chrono::system_clock::now() >= nextPatternStart) {
        currentPattern = nextPattern;
        cur = NS;
        nextChange = nextPatternStart + currentPattern.nsTime;
        nextPatternStart = std::chrono::system_clock::time_point::max();
        std::cout << "Starting new pattern." << std::endl;
    }
    if (std::chrono::system_clock::now() >= nextChange) {
        cur = cur == NS ? EW : NS;
        nextChange += (cur == NS ? currentPattern.nsTime : currentPattern.ewTime);
        std::cout << "Changing light to " << (cur == NS ? "North-South" : "Est-West") << std::endl;
    }
}

void Controler::ping() {
    nlohmann::json j = {
        {"key", "zqsd"}, // TODO: Generate a small random string or int?
        {"from", identifier}
    };
    mq.notifyServer("PING", j.dump());
    pingStart = std::chrono::steady_clock::now();
}

void Controler::notifyPedestrian(Direction d) {
    nlohmann::json j = {
        {"direction", (d == NS ? "NS" : "EW")},
        {"sender", identifier}
    };
    mq.notifyServer("PEDESTRIAN", j.dump());
}

void Controler::handleMessage(std::string msg) {
    std::cout << "Message received " << msg << std::endl;
    std::string cmd;
    nlohmann::json j;

    // Fetch the command and data (if any)
    auto jsonStart = std::find(msg.begin(), msg.end(), '{');
    if (jsonStart == msg.end()) { // There is no data
        cmd = msg;
    } else {
        cmd = std::string(msg.begin(), jsonStart);
        j = nlohmann::json::parse(jsonStart, msg.end());
    }

    // Handle the command
    if (cmd == "stop") {
        std::cout << "Received stop command, switching everything off. Have fun with no trafic lights." << std::endl;
        isRunning = false;
    } else if (cmd == "pong") {
        std::cout << "Ping response received in " << std::chrono::duration_cast<std::chrono::milliseconds>(std::chrono::steady_clock::now() - pingStart).count() << "ms." << std::endl;
    } else if (cmd == "pattern") {
        std::cout << "New pattern received." << std::endl;
        nextPattern.nsTime = std::chrono::seconds(j["NS"]);
        nextPattern.ewTime = std::chrono::seconds(j["EW"]);

        // Gracefully handle weird commands
        if (j.find("startTime") == j.end() || j["startTime"] == 0) {
            nextPatternStart = std::chrono::system_clock::now();
        } else nextPatternStart = std::chrono::system_clock::time_point(std::chrono::seconds(j["startTime"]));
        // TODO: Timestamp
    }
}
