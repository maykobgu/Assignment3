#include <stdlib.h>
#include <connectionHandler.h>
#include <ClientToServer.h>
#include <boost/thread.hpp>


ClientToServer::ClientToServer(ConnectionHandler *connectionHandler, bool &isTerminate) : connectionHandler(
        connectionHandler), isTerminated(isTerminate) {};


void ClientToServer::run() {
    try {
        while (!isTerminated) {
            const short bufsize = 1024;
            char buf[bufsize];
            std::cin.getline(buf, bufsize);
            std::string line(buf);
            int len = line.length();
            std::vector<std::string> vector;
            vector = insertToVector(vector, line, " ");
            char opCodeArray[2];
            short opcode;
            if (vector[0] == "REGISTER") {
                opcode = 1;
                shortToBytes(opcode, opCodeArray);
                connectionHandler->sendBytes(opCodeArray, 2);
                connectionHandler->sendFrameAscii(vector[1], '\0');
                connectionHandler->sendFrameAscii(vector[2], '\0');
            } else if (vector[0] == "LOGIN") {
                opcode = 2;
                shortToBytes(opcode, opCodeArray);
                connectionHandler->sendBytes(opCodeArray, 2);
                connectionHandler->sendFrameAscii(vector[1], '\0');
                connectionHandler->sendFrameAscii(vector[2], '\0');
            } else if (vector[0] == "LOGOUT") {
                opcode = 3;
                shortToBytes(opcode, opCodeArray);
                connectionHandler->sendBytes(opCodeArray, 2);
            } else if (vector[0] == "FOLLOW") {
                opcode = 4;
                shortToBytes(opcode, opCodeArray);
                connectionHandler->sendBytes(opCodeArray, 2);
                connectionHandler->sendFrameAscii(vector[1], '\0');
                connectionHandler->sendFrameAscii(vector[2], '\0');
                for (int i = 3; i < vector.size(); i++) {
                    connectionHandler->sendFrameAscii(vector[i], '\0');
                }
            } else if (vector[0] == "POST") {
                opcode = 5;
                shortToBytes(opcode, opCodeArray);
                connectionHandler->sendBytes(opCodeArray, 2);
                connectionHandler->sendFrameAscii(line.substr(4), '\0');
            } else if (vector[0] == "PM") {
                opcode = 6;
                shortToBytes(opcode, opCodeArray);
                connectionHandler->sendBytes(opCodeArray, 2);
                connectionHandler->sendFrameAscii(vector[1], '\0');
                int length = vector[1].length();
                connectionHandler->sendFrameAscii(line.substr(4 + length), '\0');
            } else if (vector[0] == "USERLIST") {
                opcode = 7;
                shortToBytes(opcode, opCodeArray);
                connectionHandler->sendBytes(opCodeArray, 2);
            } else if (vector[0] == "STAT") {
                opcode = 8;
                shortToBytes(opcode, opCodeArray);
                connectionHandler->sendBytes(opCodeArray, 2);
                connectionHandler->sendFrameAscii(vector[1], '\0');
            }
            if (!connectionHandler->sendLine(line)) {
                std::cout << "Disconnected. Exiting...\n" << std::endl;
                break;
            }
        }
    }
    catch (boost::thread_interrupted const &) {}
}

std::vector<std::string>
ClientToServer::insertToVector(std::vector<std::string> vector, std::string line, std::string delimiter) {
    size_t pos = 0;
    while ((pos = line.find(delimiter)) != std::string::npos) {
        vector.push_back(line.substr(0, pos));
        line.erase(0, pos + delimiter.length());
    }
    vector.push_back(line.substr(0, pos));
    return vector;
}

void ClientToServer::shortToBytes(short num, char *bytesArr) {
    bytesArr[0] = ((num >> 8) & 0xFF);
    bytesArr[1] = (num & 0xFF);
}


ClientToServer::~ClientToServer() {

}