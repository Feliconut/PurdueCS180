package Exceptions;

import Field.Conversation;

public class ConversationNotFoundException extends Exception{
    Conversation conversation;

    public ConversationNotFoundException (){}

    public ConversationNotFoundException (String message){
        super(message);
    }



    public ConversationNotFoundException(Conversation conversation){
        this.conversation = conversation;
    }


}
