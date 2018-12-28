#include <stdlib.h>
#include <connectionHandler.h>
#include <ServerToClient.h>

ServerToClient::ServerToClient(ConnectionHandler *connectionHandler, bool &isTerminated) : connectionHandler(
        connectionHandler), isTerminated(isTerminated) {};

void ServerToClient::run() {
    while (1) {
        std::string answer;
        if (!connectionHandler->getLine(answer)) {
            std::cout << "Disconnected. Exiting...\n" << std::endl;
            connectionHandler->close();
            break;
        }








        int len = answer.length();
        answer.resize(len - 1);
        std::cout << answer << std::endl;
        if (answer == "ACK signout succeeded") {
            std::cout << "Exiting...\n" << std::endl;
            connectionHandler->close();
            isTerminated = false;
            break;
        }
    }
}

short ServerToClient::bytesToShort(char *bytesArr) {
    short result = (short) ((bytesArr[0] & 0xff) << 8);
    result += (short) (bytesArr[1] & 0xff);
    return result;
}

ServerToClient::~ServerToClient(){

}