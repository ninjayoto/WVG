# WVG
![WVG](https://s9.postimg.cc/xrh88avin/main.jpg)

WVG, or Will's View Generator, is a simple tool created to help you generate realistic views for your website.
It uses many different user agents, referers, and proxies within the TOR network to accomplish this task.
Moreover, unlike other common view generators, this program doesn't flood the server with requests but does it at a slower pace and at random intervals to make the traffic seem more realistic.  Do not use this program on websites if it violates your region's laws or your host's rules.
This program was created for a programming challenge in high school, so it still needs to be polished.  Please report all issues on my Github page.

## Getting Started

This program only works on Windows (tested on 7, 8.1, and 10) and some distributions of GNU/Linux (tested on Debian and Ubuntu).
You can either download the program in the releases section or compile it yourself.

### Using the Binaries
Extract the program and run WVG.jar for the GUI interface or type java -jar WVG.jar on CMD/terminal.  Linux users should do this command with sudo priveleges.

### Compiling From Source
Retrieve the source code on this project.  Then, load up the .java files in your editor and compile them with the required libraries (check the bin to see which libraries are needed).
For Windows users, you should compile the provided batch scripts with a batch script to exe compiler (the scripts - runtor and stoptor - are located in resources/wintor).  For Linux users, compile the shell scripts (located in resources/linuxtor) with SHC (please use the -r option but do not make the binary untraceable).
Afterwards, bring the jar libraries, compiled jar file into its own folders.  Copy over the 5000sites and resources folder too.

## Usage
Click on WVG.jar to run the GUI.  You can also run the CLI version from CMD/terminal.  Usage is as follows:
 * -h,--help            Shows help for this program.
 * -t,--time <arg>      The number of views you want for the site.
 * -w,--website <arg>   The website you want to generate views for. Make
                      sure to enter a proper url.
Currently, you may see outputs of socket exceptions during its usage in the terminal.  Just ignore those because the program will still work properly.

## Authors
* **Will Liu** - *Creator* - [BitsByWill](https://github.com/BitsByWill)

## What's Next?
I hope that this program will be made better with some extra work and your feedback.  
Some future goals for this program will be:
* Better GUI
* Multithreading
* Get rid of the excess logging messages caused by some of the libraries used
* More randomized interaction with webpages - perhaps even add clicking and scrolling!
* More efficient code
* Better support on Linux
* Create a script that allows for updates

Let me know of any new features you want and please let me know of the issues!

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details
