public class MessageServerMaster
{
    public static void main(String[] args)
    {
        //Starts the system.
        MessageSystem messageSystem = new MessageSystem("users.txt","messages.txt","conversations.txt");

        //TODO starts the server, listens to new connects, and start a thread for each new client.
        new MessageServerWorker(null, messageSystem).start();
    }
}

