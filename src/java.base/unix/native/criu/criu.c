#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

int main(int argc, char *argv[]) {
    char** newargv = malloc((argc + 2) * sizeof(char*));
    newargv[0] = "sudo";
    newargv[1] = "criu";
    memcpy(newargv + 2, argv + 1, argc * sizeof(char*));
    execvp("/usr/bin/sudo", newargv);
    perror("sudo criu");
    return 1;
}
