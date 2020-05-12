
#include <stdlib.h>
#include <unistd.h>
#include <stdio.h>
#include <string.h>
#include <inttypes.h>
#include <time.h>
#include <sys/time.h>

uint64_t nanos(void) {
  struct timespec tp;
  if (0 != clock_gettime(CLOCK_MONOTONIC, &tp)) {
    perror("clock_gettime");
    exit(1);
  }
  return ((uint64_t)tp.tv_sec) * (1000 * 1000 * 1000) + (uint64_t)tp.tv_nsec;
}

uint64_t millis(void) {
  struct timeval time;
  if (0 != gettimeofday(&time, NULL)) {
    perror("gettimeofday");
    exit(1);
  }
  return ((uint64_t)time.tv_sec) * 1000 + ((uint64_t)time.tv_usec)/1000;
}

int main(int argc, char *argv[]) {
  int opt;
  uint64_t (*fn)(void) = nanos;
  while ((opt = getopt(argc, argv, "mn")) != -1) {
  switch (opt) {
    case 'n':
      fn = nanos;
      break;
    case 'm':
      fn = millis;
      break;
    default:
      break;
    }
  }

  uint64_t time = fn();
  char *msg = optind < argc ? argv[optind] : "prestart";
  printf("STARTUPTIME %" PRIu64 " %s\n", time, msg);
  return 0;
}
