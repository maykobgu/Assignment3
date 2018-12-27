#include <stdlib.h>
#include <connectionHandler.h>
#include <CSConverter.h>
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
                opCodeArray[0] = ((opcode >> 8) & 0xFF);
                opCodeArray[1] = (opcode & 0xFF);
//                shortToBytes(opcode, opCodeArray);
                connectionHandler->sendBytes(opCodeArray, 2);
                connectionHandler->sendFrameAscii(vector[1], '\0');
                connectionHandler->sendFrameAscii(vector[2], '\0');
            }
//            else if (command == "LOGIN") {
//                opcode = 2;
//                newbuf[0] = ((opcode >> 8) & 0xFF);
//                newbuf[1] = (opcode & 0xFF);
//                int newindex = 2;
//                for (int i = index + 1; i < sizeof(buf) & !end; i++) {
//                    if (buf[i] == ' ')
//                        newbuf[newindex] = '0';
//                    else if (buf[i] == '\0') {
//                        end = true;
//                        newbuf[newindex] = '0';
//                    } else
//                        newbuf[newindex] = buf[i];
//                    newindex++;
//                }
//            } else if (command == "LOGOUT") {
//                opcode = 3;
//                newbuf[0] = ((opcode >> 8) & 0xFF);
//                newbuf[1] = (opcode & 0xFF);
//            } else if (command == "FOLLOW") {
//                opcode = 4;
//                newbuf[0] = ((opcode >> 8) & 0xFF);
//                newbuf[1] = (opcode & 0xFF);
//                int newindex = 4;
//                newbuf[2] = buf[index + 1];
//                newbuf[3] = buf[index + 3];
//                for (int i = index + 4; i < sizeof(buf) & !end; i++) {
//                    if (buf[i] == ' ')
//                        newbuf[newindex] = '0';
//                    else if (buf[i] == '\0') {
//                        end = true;
//                        newbuf[newindex] = '0';
//                    } else
//                        newbuf[newindex] = buf[i];
//                }
//            } else if (command == "POST") {
//                opcode = 5;
//                newbuf[0] = ((opcode >> 8) & 0xFF);
//                newbuf[1] = (opcode & 0xFF);
//                int newindex = 2;
//                bool end = false;
//                for (int i = index + 1; i < sizeof(buf) & !end; i++) {
//                    if (buf[i] == '\0') {
//                        end = true;
//                        newbuf[newindex] = '0';
//                    } else
//                        newbuf[newindex] = buf[i];
//                    newindex++;
//                }
//            } else if (command == "PM") {
//                opcode = 6;
//                newbuf[0] = ((opcode >> 8) & 0xFF);
//                newbuf[1] = (opcode & 0xFF);
//                int newindex = 2;
//                for (int i = index + 1; i < sizeof(buf) & !end; i++) {
//                    if (buf[i] == ' ')
//                        newbuf[newindex] = '0';
//                    else if (buf[i] == '\0') {
//                        end = true;
//                        newbuf[newindex] = '0';
//                    } else
//                        newbuf[newindex] = buf[i];
//                    newindex++;
//                }
//            } else if (command == "USERLIST") {
//                opcode = 7;
//                newbuf[0] = ((opcode >> 8) & 0xFF);
//                newbuf[1] = (opcode & 0xFF);
//            } else if (command == "STAT") {
//                opcode = 8;
//                newbuf[0] = ((opcode >> 8) & 0xFF);
//                newbuf[1] = (opcode & 0xFF);
//                int newindex = 2;
//                bool end = false;
//                for (int i = index + 1; i < sizeof(buf) & !end; i++) {
//                    if (buf[i] == '\0') {
//                        end = true;
//                        newbuf[newindex] = '0';
//                    } else
//                        newbuf[newindex] = buf[i];
//                    newindex++;
//                }
//            }

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