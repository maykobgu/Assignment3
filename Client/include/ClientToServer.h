#ifndef CLIENT_TO_SERVER_
#define CLIENT_TO_SERVER_

#include <connectionHandler.h>

class ClientToServer {
private:
    ConnectionHandler *connectionHandler;
    bool &isTerminated;
public:
    ClientToServer(ConnectionHandler *connectionHandler, bool &isTerminated);

    void shortToBytes(short num, char *bytesArr);

    std::vector<std::string> insertToVector(std::vector<std::string> vector, std::string line, std::string delimiter);

    void run();

    virtual ~ClientToServer();
};

#endif