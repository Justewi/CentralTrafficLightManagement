# Light Controller
This controller is intended to be put in each crossroads. It does the
translation between the main server and the automata that actually controls the
cycle of the light signals.

##Dependencies
This controller depends on several libraries to work:

* nlohmann's JSON for modern C++ (https://nlohmann.github.io/json/)
* AMQP-CPP (https://github.com/CopernicaMarketingSoftware/AMQP-CPP)