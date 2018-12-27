#ifndef SERVER_TO_CLIENT_
#define SERVER_TO_CLIENT_

#include <connectionHandler.h>

class ServerToClient {
private:
    ConnectionHandler *connectionHandler;
    bool &isTerminated;
public:
    ServerToClient(ConnectionHandler *connectionHandler, bool &isTerminated);
    void run();
};

#endif