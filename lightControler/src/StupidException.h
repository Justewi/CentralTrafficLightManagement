#ifndef STUPIDEXCEPTION_H
#define STUPIDEXCEPTION_H

#include <string>

class StupidException : public std::exception {
    std::string msg;
    int errorCode;
public:

    StupidException(std::string _msg, int _errorCode = 0) : msg(_msg), errorCode(_errorCode) {
    };

    ~StupidException() throw () {
    };

    const char* what() const throw () {
        return (msg + " Error code: " + std::to_string(errorCode) + "\n").c_str();
    };

    int getErrorCode() const throw () {
        return errorCode;
    };
};

#endif /* STUPIDEXCEPTION_H */

