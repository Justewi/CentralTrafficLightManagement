#ifndef LOGGER_H
#define LOGGER_H

#include <ostream>

// Neat thing
// Thanks to https://stackoverflow.com/questions/17597828/c-prepend-datetime-to-console-output
// And http://gabisoft.free.fr/articles/fltrsbf1.html

class Logger : public std::streambuf {
    std::streambuf* myDest;
    std::ostream* myOwner;
    bool isAtStartOfLine;
    std::string prependData;
protected:

    int overflow(int ch) override {
        if (ch != '\n' && isAtStartOfLine) {
            myDest->sputn(prependData.data(), prependData.size());
        }
        if (isAtStartOfLine = ch == '\n')
            myDest->pubsync(); // Request sync, otherwise it won't write to file
        return myDest->sputc(ch);
    }
public:

    Logger(std::streambuf* dest, std::string dataToPrepend) : myDest(dest), myOwner(nullptr), prependData(dataToPrepend) {

    };

    Logger(std::ostream& owner, std::string dataToPrepend) : myDest(owner.rdbuf()), myOwner(&owner), prependData(dataToPrepend) {
        myOwner->rdbuf(this);
    };

    virtual ~Logger() {
        myDest->pubsync(); // Request sync, otherwise it won't write to file (bash redirection)
        if (myOwner != nullptr) {
            myOwner->rdbuf(myDest);
        }
    };

};

#endif /* LOGGER_H */

