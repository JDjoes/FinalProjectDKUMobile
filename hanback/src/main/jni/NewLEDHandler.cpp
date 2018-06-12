//
// Created by asus on 6/12/2018.
//

#include "com_binus_dku_hanback_NewLEDHandler.h"
#include <string.h>
#include <jni.h>
#include <stdio.h>
#include <stdlib.h>
#include <fcntl.h>
#include <unistd.h>
#include <termios.h>
#include <sys/mman.h>
#include <errno.h>

JNIEXPORT jint JNICALL Java_com_binus_dku_hanback_NewLEDHandler_ndkPlay
        (JNIEnv *env, jobject obj, jint v){

    int fd;
    unsigned char wval = v;

    fd = open("/dev/fpga_led", O_WRONLY);
    write (fd, &wval, sizeof(wval));
    close(fd);

    return v;
}
