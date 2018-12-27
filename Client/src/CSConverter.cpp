//#include <stdlib.h>
//#include <connectionHandler.h>
//#include <ClientToServer.h>
//#include <ServerToClient.h>
//#include <CSConverter.h>
//
//using namespace std;
//
//CSConverter::CSConverter() {};
//
//char *CSConverter::convert(char *buf) {
//    short bufsize = 1024;
//    std::cout << sizeof(buf) << std::endl;
//    char newbuf[bufsize];
//    string command = "";
//    bool found = false;
//    int index;
//    short opcode;
//    for (int i = 0; i < sizeof(buf) & !found; i++) {
//        if (buf[i] != ' ') {
//            command += std::string(1, buf[i]);
//        } else {
//            found = true;
//            index = i;
//        }
//    }
//    if (command == "REGISTER") {
//        opcode = 1;
//        shortToBytes(opcode, newbuf);
//        continueWriting(buf, newbuf, index);
//    } else if (command == "LOGIN") {
//        opcode = 2;
//        shortToBytes(opcode, newbuf);
//        int newindex = 2;
//        continueWriting(buf, newbuf, index);
//    } else if (command == "LOGOUT") {
//        opcode = 3;
//        shortToBytes(opcode, newbuf);
//    } else if (command == "FOLLOW") {
//        opcode = 4;
//        shortToBytes(opcode, newbuf);
//        followWriting(buf, newbuf, index);
//    } else if (command == "POST") {
//        opcode = 5;
//        shortToBytes(opcode, newbuf);
//        postAndStatWriting(buf, newbuf, index);
//    } else if (command == "PM") {
//        opcode = 6;
//        shortToBytes(opcode, newbuf);
//        continueWriting(buf, newbuf, index);
//    } else if (command == "USERLIST") {
//        opcode = 7;
//        shortToBytes(opcode, newbuf);
//    } else if (command == "STAT") {
//        opcode = 8;
//        shortToBytes(opcode, newbuf);
//        postAndStatWriting(buf, newbuf, index);
//    }
//    return newbuf;
//}
//
//void CSConverter::shortToBytes(short num, char *bytesArr) {
//    bytesArr[0] = ((num >> 8) & 0xFF);
//    bytesArr[1] = (num & 0xFF);
//}
//
//
//void CSConverter::continueWriting(char buf[], char newbuf[], int index) {
//    int newindex = 2;
//    bool end = false;
//
//    for (int i = index + 1; i < sizeof(buf); i++) {
//        if (buf[i] == ' ')
//            newbuf[newindex] = '0';
//        else if (buf[i] == '\0') {
//            end = true;
//            newbuf[newindex] = '0';
//        } else
//            newbuf[newindex] = buf[i];
//    }
//    newbuf[sizeof(newbuf)] = '0';
//}
//
//void CSConverter::followWriting(char buf[], char newbuf[], int index) {
//    int newindex = 4;
//    bool end = false;
//    newbuf[2] = buf[index + 1];
//    newbuf[3] = buf[index + 3];
//    for (int i = index + 4; i < sizeof(buf) & !end; i++) {
//        if (buf[i] == ' ')
//            newbuf[newindex] = '0';
//        else if (buf[i] == '\0') {
//            end = true;
//            newbuf[newindex] = '0';
//        } else
//            newbuf[newindex] = buf[i];
//    }
//}
//
//void CSConverter::postAndStatWriting(char buf[], char newbuf[], int index) {
//    int newindex = 2;
//    bool end = false;
//    for (int i = index + 1; i < sizeof(buf) &!end; i++) {
//        if (buf[i] == '\0') {
//            end = true;
//            newbuf[newindex] = '0';
//        } else
//            newbuf[newindex] = buf[i];
//        newindex++;
//    }
//}