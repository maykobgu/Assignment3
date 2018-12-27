#ifndef SCCONVERTER_
#define SCCONVERTER_

#include <string>
#include <iostream>

class SCConverter {

public:
    SCConverter();
    char* convert(char bytes[]);
    void shortToBytes(short num, char* bytesArr);
    void continueWriting(char buf[], char newbuf[], int index);
    void followWriting(char buf[], char newbuf[], int index);
    void postAndStatWriting(char buf[], char newbuf[], int index);

};

#endif