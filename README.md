# PurdueCS180
Purdue CS 180 Project 5 Group 84 See documentation below. 

**Contributors:** Felix Liu, Jiaqi Chen, Paul Fang, Silvia Yang, Teresa Huang.

# Submission

* **Felix Liu** Submitted code on Vocareum
* **Teresa Huang** Submitted Report on Brightspace
* **Paul Fang** Submitted Presentation on Brightspace
# Compile and Run

### Download and Compile

1. Download the source code.
2. Compile in an IDE of your choice.
### Run the Code

1.Run`MessageServerMaster.java`.

2.Run`MessageClient.java`and allow the program to run in parallel so that multiple clients can run at the same time.

### Quit the application

1. Click on the "X" on the upper right corner to end the client.
2. Click on the red square in IntelliJ (or an IDE of your choice) to terminate MessageServerMaster.
# Detailed Description of Eachclass

## System Classes

### `ServerMaster`

This class ensures that every user has their own thread to connect to the server.

### `ServerWorker`

This class will define what type of request is first and then sign the local variable“response” to it. Once the system finds it, it will go to find the corresponding response method. Next, the system will find the needed information and call the method from the message system to realize its objective. After collecting the information from the message system, it will send a response back to the client with the needed data.

### `Database`

This class will save all the messages from the Message System, check if there isatxt, read the data and save it into hashmap, then refresh the file, wait for the next message from the Message System, and it should have a function that can let Message System use the information which saves in the hashmap easily.

### `MessageSystem`

This classcontains many methods which are used to realize the specific implementation from the server and give a value back totheserver.

### `MessageClient`

Thisclass contains the graphical user interface and functions for clients.  There are four inner classes in MessageClient. The`MessageClient`class includes the main method and invokes the Window class. The`window`class contains the components in the graphical user interface and the action listeners. The`ClientWorker`fetches data from the server and processes data when the users do action. ClientWorker sends requests to the server and receives corresponding responses in order to implement the specified action. The`ListDisplay`is used by the JList for real-time updates to display conversations and messages.


## Data Structure Classes

### `public class Conversation`

The class stores the information of a conversation.

`String name` The name of the conversation.

`UUID[] user_uuids` An ArrayList containing the UUIDs of the members.

`UUID admin_uuid` The UUID of the administrator.

`UUID[] message_uuids` An ArrayList of the UUIDs of the messages.

### `public class Message`

The class stores the information of a message.

`UUID sender_uuid` The UUID of the message sender.

`Date time` The time that the message is sent.

`String content` The content of the message.

`UUID conversation_uuid` The UUID of the conversation that the message belongs.

### `public class User`

The class stores the information of the user.

`Credential credential` The credential of the user.

`Profile profile` The profile of the user.

### `public class Credential`

The class stores the credential of the user.

`String usrName` The username of the user.

`String passwd` The password of the user.

### `public class Profile`

The class stores the profile of the user.

`String name` The name of the user.

`int age` The age of the user

### `public abstract class Storable`

The class stores UUIDs.

`UUID uuid` A unique and exclusive used to identify objects.

### `public class EventBag`

The class stores the event that is used for live-update

`ArrayList<User> new_users` Newly created users.

`ArrayList<Conversation> new_conversations` Newly created conversations.

`HashMap<UUID, Arraylist<Message>> new_messages` Newly created messages.

`ArrayList<User> users updated_users` Previously existed users who changed their profile.

`ArrayList<Conversation> updated_conversations` Previously existed conversations that are edited.

`ArrayList<Message> updated_messages` Previously existed messages that are edited.

`ArrayList<UUID> removed_users` Users that are removed.

`ArrayList<UUID> removed_conversations` Conversations that are removed.

`ArrayList<UUID> removed_messages` Messages that are removed.

## Exception Classes

### `RequestFailedException extends Exception`

This exception could be called anyway, anything incorrect could be concludedastherequestfailed.

### `AuthorizationException extends RequestFailedException`

This exception will bethrowniftheuser tries to action that is outside their authority such as deleting a message that is not created by the user.

### `ConversationNotFoundException extends RequestFailedException`

This exception will be thrown if the provided conversation_uuid could not be found.

### `IllegalContentExceptionx extends RequestFailedException`

This exception will be thrown if there are special characters in the content.

### `InvalidConversationNameException extends RequestFailedExceptioin`

This exception will be thrown if the name of the conversation is invalid.

### `InvalidPasswordException extends RequestFailedExceptioin`

This exception will be thrown if the provided password does not match the credential in the system.

### `InvalidUsernameException extends RequestFailedExceptioin`

This exception will be thrown if the provided username could not be found.

### `LoggedInException extends RequestFailedExceptioin`

This exception will be thrown when the user tries to log in to the same account multiple times while the account is already logged in.

### `MessageNotFoundException extends RequestFailedExceptioin`

This exception will be thrown if the provided message could not be found.

### `NotLoggedInException extends RequestFailedExceptioin`

This exception will be thrown when the user is not logged in and tries to do action.

### `UserExistsException extends RequestFailedExceptioin`

This exception will be thrown when the user tries to register for an account, but the username entered has been already taken.

### `UserNotFoundException extends RequestFailedExceptioin`

This exception will be thrown when the provided user does not exist.

### `RequestParsingException extends Exception`

This exception will be thrown if any failure occurs when parsing the request to a specific request.


## Communication Classes

### `public abstract Request implements Serializable`

The most basic request type. All requests extend this class.

`UUID uuid` The universally unique identifier of the request.

### `public Response extends Request`

The most basic response type. All responses extend this class.

`boolean state` Whether the request successes or fails.

`String msg` Some message to be passed.

`UUID request_uuid` UUID of the corresponding request.

`equestFailedException exception`If`state==false`, this is the exception that caused the failure. Otherwise, it's`null`.


---


### `AuthenticateRequest`

This request is for login. If you are not logged in, you have no permission to perform other operations except register.

`Credential credential` Login credentials, including username and password.

**→ The Response if the request is successful**

`Response`

This is the most basic Responsewith no special fields. The following are all subclasses of Response.

`Boolean status` True means success, false means failure.

`String msg`message. Can be `null`.

`UUID request_uuid` TheUUIDforthe request.

**!! Pos****s****ible Exceptions for a failed request**

`UserNotFoundException` Username does not exist

`InvalidPasswordException` Password is incorrect

`InvalidUsernameException` The username is invalid

`LoggedInException` You are already logged in, you cannot log in again


### `GetUserByNameRequest`

Get the user's UUID by the username.

`String Name` The user's username.

**→ The Response if the request is successful**

`GetUserByNameResponse`

The specific response that allows the users to get usernames.

`UUID user_uuid` The corresponding UUID of the user

**!! Possible Exceptions for a failed request**

`UserNotFoundException` The provided username does not exist.

#### 

### `EditProfileRequest`

Changethe userprofile.

`Profile profile` New profile

**→ The Response if the request is successful**

`Response`

The basic response.

**!! Possible Exceptions for a failed request**

`NotLoggedInException` The user tries to edit the profile but is logged out.


### `RegisterRequest`

Registration.

`Credential credential`

`Profile profile`

**→ The Response if the request is successful**

RegisterResponse

`UUID uuid` Your ownUUID.

**!! Possible Exceptions for a failed request**

`UserExistsException` User already exists

`InvalidUsernameException` The username is invalid

`InvalidPasswordException` The password is invalid


### `DeleteAccountRequest`

Delete the current account.

`Credential credential`

**→ The Response if the request is successful**

`response`

The basic response.

**!! Possible Exceptions for a failed request**

`AuthorizationException` Credential error

`UserNotFoundException` User does not exist

#### 

### `LogOutRequest`

Log out.

**→ The Response if the request is successful**

`response`

The basic response.

**!! Possible Exceptions for a failed request**

`NotLoggedInException` You are not currently logged in

## 

### `PostMessageRequest`

Sends a message.

`UUID conversation_uuid` The UUID of the conversaion

`Message message` Note: Only `message.content` is used. Other fields are neglected / auto-generated bythesystem.

**→ The Response if the request is successful**

`PostMessageResponse`

`UUID message_uuid` The UUID of the message posted

`Date date` The time when the message was actually sent successfully.

**!! Possible Exceptions for a failed request**

`NotLoggedInException` Not logged in.

`ConversationNotFoundException` The group chat does not exist or does not belong to you.

`AuthorizationException` You do not have permission to change this group chat.

`IllegalContentException` The content of the message is illegal. It is too long or contains illegal characters.

#### 

### `EditMessageRequest`

Edit a message of your own.

`UUID message_uuid` The message's UUID being edited

`String content` The new content

**→ The Response if the request is successful**

`EditMessageResponse`

`Date dateEdited` The actual time of successful modification

**!! Possible Exceptions for a failed request**

`NotLoggedInException` Not logged in.

`MessageNotFoundException` The message does not exist, or you should not see it.

`AuthorizationException` Does not have permission to change this information (probably because it belongs to someone else)

`IllegalContentException` The content of the message is illegal. It is too long or contains illegal characters.

### 

### `DeleteMessageRequest`

Delete a message.

`UUID message_uuid` The message's UUID being deleted

**→ The Response if the request is successful**

`response`

The basic response.

**!! Possible Exceptions for a failed request**

`NotLoggedInException` Not logged in.

`MessageNotFoundException` The message does not exist, or you should not see it.

`AuthorizationException` Does not have permission to change this information (probably because it belongs to someone else)

#### 

### `CreateConversationRequest`

Creates a new group chat.

`String name` The name of the conversation

`UUID[] user_uuids` The users' UUIDs (If it is empty, create a single chat)

**→ The Response if the request is successful**

`CreateConversationRsponse`

The specific response to create a conversation.

`UUID conversation_uuid` The UUID of the conversation

**!! Possible Exceptions for a failed request**

`NotLoggedInException` Not logged in

`InvalidConversationNameException` Conversation name not valid

`UserNotFoundException` The invited users do not exist

#### 

### `DeleteConversationRequest`

Delete the group chat.

`UUID conversation_uuid` The UUID of the conversation you would like to delete

**→ The Response if the request is successful**

`response`

The basic response.

**!! Possible Exceptions for a failed request**

`NotLoggedInException` Not logged in

`ConversationNotFoundException` The conversation does not exist.


### `RenameConversationRequest`

Rename the group chat.

`UUID conversation_uuid` The UUID of the conversation

`String name` The name of the conversation

**→ The Response if the request is successful**

`response`

The basic response.

**!! Possible Exceptions for a failed request**

`NotLoggedInException` Not logged in

`InvalidConversationNameException` Conversation name not valid

`ConversationNotFoundException` The conversation does not exist.

#### 

### `AddUser2ConversationRequest`

Pulls the user into the group chat.

`UUID user_uuid` The invited user's UUID

`UUID conversation_uuid` The conversation UUID

**→ The Response if the request is successful**

`response`

The basic response.



**!! Possible Exceptions for a failed request**

`NotLoggedInException` Not logged in

`ConversationNotFoundException` The conversation does not exist.

`UserNotFoundException` The invited users do not exist

`AuthorizationException` You are not the administrator of the conversation



### `RemoveUserFromConversationRequest`

Remove the user from the group chat.

`UUID user_uuid` The user's UUID you want to remove

`UUID conversation_uuid` The conversation UUID

**→ The Response if the request is successful**

`response`

The basic response.

**!! Possible Exceptions for a failed request**

`NotLoggedInException` Not logged in

`ConversationNotFoundException` The conversation does not exist, or you do not belong to the group chat.

`UserNotFoundException` The user does not exist

`AuthorizationException` You are not the administrator of the conversation and cannot remove others.

`RequestFailedException` You cannot remove yourself.


### `SetConversationAdminRequest`

Set the group chat administrator.

`UUID user_uuid` The user's UUID you want to remove

`UUID conversation_uuid` The conversation UUID

**→ The Response if the request is successful**

`response`

The basic response.

**!! Possible Exceptions for a failed request**

`NotLoggedInException` Not logged in

`ConversationNotFoundException` The conversation does not exist, or you do not belong to the group chat.

`UserNotFoundException` The user does not exist

`AuthorizationException` You don't have permission to set admin.


### `QuitConversationRequest`

The user voluntarily leaves the group chat. If the user is currently an administrator, the system will automatically set an administrator randomly. If the user is the last user in the group chat, then the group chat will be deleted.

`UUID conversation_uuid`T he conversation UUID

**→ The Response if the request is successful**

`response`

The basic response.

**!! Possible Exceptions for a failed request**

`NotLoggedInException` Not logged in

`ConversationNotFoundException` The conversation does not exist, or you do not belong to the group chat.



## ## Information pull

### `ListAllUsersRequest`

List the UUIDs of all users

**→ The Response if the request is successful**

`ListAllUsersResponse`

List all the user's UUIDs.

`UUID[] user_uuids` The UUIDs of all the users

**!! Possible Exceptions for a failed request**

`NotLoggedInException` Not logged in

#### 

### `ListAllConversationsRequest`

List all group chat UUIDs, including only the group chats that the user has participated in

**→ The Response if the request is successful**

`ListAllUserConversationsResponse`

The specific response to the request.

`UUID[] conversation_uuids` The UUIDs of all the conversations

**!! Possible Exceptions for a failed request**

`NotLoggedInException` Not logged in

#### 

### `ListAllMessagesRequest`

List all the message UUIDs of a group chat.

`UUID conversation_uuid` The UUID of the conversation

**→ The Response if the request is successful**

`ListAllMessagesResponse`

The specific response to list all messages.

`Messages[] user_messages` An array of messages sent by all users in this conversation

**!! Possible Exceptions for a failed request**

`NotLoggedInException` Not logged in

`ConversationNotFoundException` The conversation does not exist, or you do not belong to the group chat.

#### 

### `GetUserRequest`

Pull a userby the user's UUID, but cannot see the password

`UUID user_uuid` The UUID of the user

**→ The Response if the request is successful**

`GetUserResponse`

The specific response to get the user.

`User user` The corresponding user.

**!! Possible Exceptions for a failed request**

`NotLoggedInException` Not logged in

`UserNotFoundException` The user does not exist

#### 

### `GetConversationRequest`

Pull a group chatby the conversation's UUID

`UUID conversation_uuid` The UUID of the conversation



**→ The Response if the request is successful**

`GetConversationResponse`

The specific response to get the conversation

`Conversation conversation` The corresponding conversation

**!! Possible Exceptions for a failed request**

`NotLoggedInException` Not logged in

`ConversationNotFoundException` The conversation does not exist, or you do not belong to the group chat.

#### 

### `GetMessageRequest`

Pulla messageby the message's UUID

`UUID message_uuid` The UUID of the message

**→ The Response if the request is successful**

`GetMessageResponse`

The specific response to get the message

`Message message` The corresponding message

**!! Possible Exceptions for a failed request**

`NotLoggedInException` Not logged in

`MessageNotFoundException` The message does not exist

#### 

### `GetMessageHistoryRequest`

Pull the chat history of a group chat.

`UUID conversation_uuid` The UUID of the conversation

**→ The Response if the request is successful**

`GetMessageHistoryResponse`

The specific response to get the message history

`Message[] messages` All the messages in the corresponding conversation

**!! Possible Exceptions for a failed request**

`NotLoggedInException` Not logged in

`ConversationNotFoundException` The conversation does not exist, or you do not belong to the group chat.


### `GetEventFeedRequest`

Update new users, group chats,and messages. Used to implement the ***live update***.The client should request a new event every time. Incidents include:

-Add

-Rename/Edit

-Delete

****

**→ The Response if the request is successful**

`GetEventFeedResponse`

The specific response containing all the updated information.

`User[] new_users` New users.

`Conversation[] new_conversations` New group chat.

`HashMap<UUID, Message[] new_messages` All new messages for each group chat.

`User[] updated_users users` Users who existed before, but were edited.

`Conversation[] updated_conversations` A group chat that existed before but was edited.

`Message[] updated_messages` Messages that existed before, but were edited.

`UUID[] removed_users` The users who are removed from a conversation

`UUID[] removed_conversations` The conversations that are removed

`UUID[] removed_messages` The messages that are removed

**!! Possible Exceptions for a failed request**

`NotLoggedInException` Not logged in



# Testing

## AutomaticTesting

All of the automatic tests are conducted with JUnit 4. And for each method in Database and MessageSystem, one successful case and one exception expected case are provided.


### Test for Database

The Database test is included in DatabaseTest.java, which contains a write method test. The write method contains all the methods that been used in the Database. So successful running of the write method will automatically mean that other methods are running without problems.

Note: this test case can be found at`DatabaseTest.java`in the root folder.


### Test for MessageSystem

The MessageSystem test is more complicated than the database test. It is in the MessageSystemTest.java. Also, the adding, remove and edit method of each field had been tested with different Test methods. For instance, the test method for getUser included the addUser and deleteUser method so successful running of the getUserTest will approve that addUser and deleteUser could run in that situation. Another addUserExceptionExpected test method, which included throwing an expected exception when certain invalid input is provided, is also written in the test class. Every method in the MessageSytem contains an exception expected test case and a successfully running test case, to make sure the program didn't crash even invalid input is provided.

Note: this test case can be found at`MessageSystemTest.java`in the root folder.


## Manual Testing

|**User’s action**|**Expected   response**|**Test response**|
|:----|:----|:----|
|Start Client and   Server.|Show the sign-in page.|Successfully shows.|
|Click the register button.|Show the register page.|Successfully shows.|
|Sign in with a non-exist username.|Proper exception prompt shows.|Exception prompt shows as expected.|
|While registering, enter valid input for name, age, username, and password.|Create an account successfully.|Successfully creates.|
|While registering, enter valid input for name, username, and password, but entering an invalid value for age. (Entered -1)|Proper exception prompt shows.|Exception prompt shows as expected.|
|While registering, enter valid input for name, username, and password, but entering an invalid value for age. (Entered “a”)|Proper exception prompt shows.|Exception prompt shows as expected.|
|While registering, enter valid input for name, age, and password, but entering a username that already exists.|Proper exception prompt shows.|Exception prompt shows as expected.|
|After registering, sign in with the proper username and password.|Show the chatting room.|Successfully shows.|
|After registering, sign in with the proper username but enter the wrong password.|Proper exception prompt shows.|Exception prompt shows as expected.|
|The main menu will be visible after signing in, then a new chat could be set up with the proper group name and invited users. Enter a valid group name and invite nobody.|Show the group chatting room.|Successfully shows.|
|Enter a valid group name and invite another user to use a username.|Show the group chatting room with that person.|Successfully shows.|
|Invite a person that does not exist.|Proper exception prompt shows.|Exception prompt shows as expected.|
|Sending a   message in group chat.|Show the conversation with the current date and time.|Successfully shows.|
|Click setting and change profile, enter valid name and age.|Shows edited profile in the My profile section.|Successfully shows.|
|Click setting and change profile, enter the valid name but invalid age.|Proper exception prompt shows.|Exception prompt shows as expected.|
|Delete current account, and back to the log-in section, trying to log-in with that account.|Proper exception prompt shows.|Exception prompt shows as expected.|
|A creates a conversation and adds B to the conversation.|The conversation appears in B's conversation list.|Successfully shows.|
|One person sends a message.|All the members in the conversation see the message within seconds.|Successfully shows.|
|A sends a message and B tries to edit the message.|Proper exception prompt shows.|Exception prompt shows as expected.|
|A sends a message and B tries to delete the message.|Proper exception prompt shows.|Exception prompt shows as expected.|
|Edit the message then export.|The file shows edited messages.|Successfully shows.|
|Delete the message then export.|The file does not show the deleted message.|The deleted message was not shown.|
|Create a new conversation, send a message, exit the conversation, then re-enter the conversation.|The sent message is retained.|Successfully shows.|
|Create some conversations, log out, then log back in.|The conversation history is retained.|Successfully shows.|
|Log out then log back in, enter the conversation.|The message history is retained.|Successfully shows.|
|Delete a conversation from A's list.|The deletion does not impact B's conversation list.|Successfully shows.|
