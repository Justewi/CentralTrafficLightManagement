#include "Controler.h"
#include <nlohmann/json.hpp>

Controler::Controler(std::string identifier, std::string serverAddr, unsigned int serverPort)
:
identifier(identifier),
cur(NS),
currentPattern({nsTime : std::chrono::seconds(10), ewTime : std::chrono::seconds(10)}),
lastChange(std::chrono::steady_clock::now()),
mq(identifier, serverAddr, serverPort, [&](std::string msg) {
    std::cout << "Message received " << msg << std::endl;
    std::string cmd;
    nlohmann::json j;
    auto jsonStart = std::find(msg.begin(), msg.end(), '{');
    if (jsonStart == msg.end()) { // There is no data
        cmd = msg;
    } else {
        cmd = std::string(msg.begin(), jsonStart);
                j = nlohmann::json::parse(jsonStart, msg.end());
    }

    if (msg == "stop") {
        std::cout << "Received stop command, switching everything off. Have fun with no trafic lights." << std::endl;
                isRunning = false;
    } else if (msg == "pong") {
        std::cout << "Ping response received in " << std::chrono::duration_cast<std::chrono::milliseconds>(std::chrono::steady_clock::now() - pingStart).count() << "ms." << std::endl;
    } else if (msg == "pattern") {

        std::cout << "New pattern received. Starting it." << std::endl;
                int nsTimer = j["NS"];
                int ewTimer = j["EW"];
                // TODO: Timestamp

    }
}) {
}

Controler::~Controler() {
}

void Controler::update() {
    if (std::chrono::steady_clock::now() - lastChange > (cur == NS ? currentPattern.nsTime : currentPattern.ewTime)) {
        cur = cur == NS ? EW : NS;
        lastChange = std::chrono::steady_clock::now();
        std::cout << "Changing light to " << (cur == NS ? "North-South" : "Est-West") << std::endl;
    }
}

void Controler::ping() {
    mq.notifyServer("PING", "{\"key\":\"qsd\",\"from\":\"" + identifier + "\"}");
    pingStart = std::chrono::steady_clock::now();
}

void Controler::notifyPedestrian(Direction d) {
    // TODO: We can't identify which light sent this message
    mq.notifyServer("PEDESTRIAN", std::string("{\"direction\":\"") + (d == NS ? "NS" : "EW") + "\"}");
}
