# Robocar Launcher

### Foreword

*The file `README_ORIGINAL.md` contains all information related to the Robocar World Championship and the research platform it uses. This file focuses on making things work to participate in the Championship.*

### Installation

Firstly visit the original FAQ site and install all these packages that required to build Robocar City Emulator Platform on Ubuntu:

```
https://github.com/nbatfai/robocar-emulator/wiki/FAQ
```

Secondly do these stuff:

#### Robocar City Emulator

```
rcemu$ autoreconf --install
libtoolize: putting auxiliary files in `.'.
libtoolize: copying file `./ltmain.sh'
configure.ac:42: installing './config.guess'
configure.ac:42: installing './config.sub'
configure.ac:22: installing './install-sh'
configure.ac:22: installing './missing'
src/Makefile.am: installing './depcomp'
configure.ac: installing './ylwrap'
rcemu$
```

```
rcemu$ ./configure 
...
checking for the Boost thread library... (cached) yes
checking boost/asio.hpp usability... yes
checking boost/asio.hpp presence... yes
checking for boost/asio.hpp... yes
checking for osmium/osm/relation.hpp... yes
checking for osmium/io/any_input.hpp... yes
checking for library containing shm_open... -lrt
checking for shm_open... yes
checking for flex... flex
checking lex output file root... lex.yy
checking lex library... none needed
checking whether yytext is a pointer... no
checking for flex... flex
checking that generated files are newer than configure... done
configure: creating ./config.status
config.status: creating Makefile
config.status: creating src/Makefile
config.status: executing depfiles commands
config.status: executing libtool commands
rcemu$ 
```

```
rcemu$ make
...
make[1]: Leaving directory `/home/nbatfai/ROBOCAR/temp/robocar-emulator/justine/rcemu/src'
make[1]: Entering directory `/home/nbatfai/ROBOCAR/temp/robocar-emulator/justine/rcemu'
make[1]: Nothing to be done for `all-am'.
make[1]: Leaving directory `/home/nbatfai/ROBOCAR/temp/robocar-emulator/justine/rcemu'
rcemu$
```

#### Car Window

```
rcwin$ mvn clean compile package site assembly:assembly
```

#### Robocar Launcher

```
RobocarLauncher$ mvn package
```

### To run the Launcher

```
RobocarLauncher/target$ java -jar RobocarLauncher-1.0-SNAPSHOT.jar
```
