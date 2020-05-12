/** \file */

#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <signal.h>

#define RESTORE_SIGNAL   (SIGRTMIN + 2)

#define LOG "/tmp/restore4.log"

#define MSGPREFIX "restore-script: "

static int post_resume(void) {
	char *pidstr = getenv("CRTOOLS_INIT_PID");
	if (!pidstr) {
		fprintf(stderr, MSGPREFIX "can not find CRTOOLS_INIT_PID env\n");
		return 1;
	}
	int pid = atoi(pidstr);

    union sigval sv = { .sival_int = 0 };
	if (-1 == sigqueue(pid, RESTORE_SIGNAL, sv)) {
		perror(MSGPREFIX "sigqueue");
		return 1;
	}

	return 0;
}

static int restore_failed(void) {
	dup2(2, 1);
	printf("Troubles with restore: " LOG ":\n");
	system("tail " LOG);
	return 0;
}

/** Kicks VM after restore.
 * Started by CRIU on certain phases of restore process. Does nothing after all
 * phases except "post-resume" which is issued after complete restore. Then
 * send signal via with ID attached to restored process. \ref launcher should
 * pass the ID via ZE_CR_NEW_ARGS_ID env variable.
 */
int main(int argc, char *argv[]) {
	char *action = getenv("CRTOOLS_SCRIPT_ACTION");
	if (!action) {
		fprintf(stderr, MSGPREFIX "can not find CRTOOLS_SCRIPT_ACTION env\n");
		return 1;
	}

	if (!strcmp(action, "post-resume")) {
		return post_resume();
	}

	if (!strcmp(action, "restore-failed")) {
		return restore_failed();
	}

	return 0;
}
