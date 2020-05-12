
#include <sys/types.h>
#include <sys/wait.h>

int main(int argc, char *argv[]) {
	int status;
	pid_t pid = waitpid(-1, &status, 0);
	if (WIFEXITED(status)) {
		return WEXITSTATUS(status);
	}

	return 1;
}
