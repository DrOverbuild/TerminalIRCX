# TerminalIRCX
TerminalIRCX is a Java program run in the Terminal app on Mac or Linux, or the Command Prompt app on Windows. It uses
the <a href="https://code.google.com/p/pircbotx/">pircbotx</a> library to connect to IRC servers, and the 
<a href="https://github.com/jline/jline2">JLine 2</a> library to manipulate the console.

The letter X at the end of the name of this project is there because I had started a project called "TerminalIRC",
which used the <a href="http://www.jibble.org/pircbot.php">pircbot</a> library, but then I realized there were some 
limitations to this library because it is no longer updated. So I found a fork of pircbot, which is still being 
updated, called pircbotx. I then created the new project, which is named "TerminalIRCX" because I'm using the 
pircbotx library.

### Running TerminalIRCX
TerminalIRCX requires Java SE 8 or later. Once Java is installed, download the latest version of TerminalIRCX from 
the releases page. Then open Terminal or Command Prompt, depending on the OS you use. Change the current directory 
of the console by typing `cd (directory)`, replacing `(directory)` to the directory the downloaded jar file is in.  
For example, if you are on a Mac, and the jar file is in your downloads folder, you would type `cd ~/Downloads/`. If
you are on Windows, and the jar file is on your desktop, you would type `cd Destop`.

Now it's time to run TerminalIRCX. You will need to type `java -jar TerminalIRCX.jar (server ip) (nick) (channel)`. 
Of course you will need to replace `(server ip)` with the ip of the server you want to connect to, `(nick)` with the 
desired nick you wish to use (will also be used as your real name and login), and `(channel)` with the desired 
channel you wish to join after connecting. 

If you want to connect to irc.tawx.net with a nick of "RandomNick" and join the channel #banana, you would type
`java -jar TerminalIRCX.jar irc.tawx.net RandomNick banana`. The hashtag in front of banana is optional.

### Using TerminalIRCX
If you want to join additional channels after connecting to the server, type `/channel join (channel)`, replacing
`(channel)` with the desired channel. Again, the hashtag is optional. If you are an operator in a channel, you can
use `/topic` to change the topic, as well as `/deop`. If you wish to know who's in a channel, type `/users`. If you 
wish to leave a channel, type `/channel leave (channel)`, replacing `(channel)` with the channel you want to leave.

All messages from every channel are printed to the same output. Most IRC clients will separate the messages from 
different channels into tabs, but we can't do that with a console application. Every message will have the channel 
name in front of it. For example, the line `#banana <tenny1028> yada yada yada` means that tenny1028 said "yada yada 
yada" in the #banana channel. Your prompt will look something like this: `#banana <RandomNick> `. This means that 
when you type your message and hit return, it will be sent to the #banana channel. If you have joined multiple 
channels and you want to send a message to a different channel than the one in your prompt, just type `/channel 
switch (channel)`, replacing `(channel)` with a connected channel.

### Not Fully Functional Yet
Currently not every event has been handled, and there are some commands that need to be added. Here's a list of 
features that are planned to be added:

* The /help command
* The /whois command
* The /deop command
* The /kick command
* The /ban command
* Word wrapping
* Logs (All messages recorded to a txt file)
* The rest of the events to be handled

These are the features that have come to mind. There probably will be more features in the future.
