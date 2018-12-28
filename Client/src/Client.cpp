#include <stdlib.h>
#include <connectionHandler.h>
#include <ClientToServer.h>
#include <ServerToClient.h>
#include <boost/thread.hpp>

/**
* This code assumes that the server replies the exact text the client sent it (as opposed to the practical session example)
*/
int main(int argc, char *argv[]) {
//    if (argc < 3) {
//        std::cerr << "Usage: " << argv[0] << " host port" << std::endl << std::endl;
//        return -1;
//    }
//    std::string host = argv[1];
//    short port = atoi(argv[2]);
//
//    ConnectionHandler connectionHandler(host, port);
    ConnectionHandler connectionHandler("127.0.0.1", 7777);
    bool isTerminated = false;

    if (!connectionHandler.connect()) {
//        std::cerr << "Cannot connect to " << host << ":" << port << std::endl;
        std::cerr << "Cannot connect to " << "127.0.0.1" << ":" << "7777" << std::endl;
        return 1;
    }

//    ServerToClient serverToClient(&connectionHandler, isTerminated);
//    ClientToServer clientToServer(&connectionHandler, isTerminated);

    ClientToServer *clientToServer = new ClientToServer(&connectionHandler,isTerminated );
    ServerToClient *serverToClient = new ServerToClient(&connectionHandler,isTerminated );

    boost::thread serverToClientThread(&ServerToClient::run, serverToClient);
    boost::thread clientToServerThread(&ClientToServer::run, clientToServer);

    serverToClientThread.join();
    clientToServerThread.interrupt();

    delete clientToServer;
    delete serverToClient;
    return 0;

//
//c->run();

}
