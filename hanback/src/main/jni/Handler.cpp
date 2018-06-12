//
// Created by asus on 6/12/2018.
//

#include "com_binus_dku_hanback_Handler.h"
#include <string.h>
#include <jni.h>
#include <stdio.h>
#include <stdlib.h>
#include <fcntl.h>
#include <unistd.h>
#include <termios.h>
#include <sys/mman.h>
#include <errno.h>

#define TEXTLCD_BASE 0xbc
#define TEXTLCD_DD_ADDRESS _IOW(TEXTLCD_BASE,7,int)

struct strcommand_variable{
    char rows;
    char nfonts;
    char display_enable;
    char cursor_enable;
    char nblink;
    char set_screen;
    char set_rightshit;
    char increase;
    char nshift;
    char pos;
    char command;
    char strlength;
    char buf[16];
};

static struct strcommand_variable strcommand;
static int initialized = 0;

void initialize(){
    if(!initialized){
        strcommand.rows = 0;
        strcommand.nfonts = 0;
        strcommand.display_enable = 1;
        strcommand.cursor_enable = 0;
        strcommand.nblink = 0;
        strcommand.set_screen = 0;
        strcommand.set_rightshit = 1;
        strcommand.increase = 1;
        strcommand.nshift = 0;
        strcommand.pos = 10;
        strcommand.command = 1;
        strcommand.strlength = 16;
        initialized = 1;
    }
}

JNIEXPORT jint JNICALL Java_com_binus_dku_hanback_Handler_ndkPlay
        (JNIEnv *env, jclass c, jint v){

    int fd;
    unsigned char wval = v;

    fd = open("/dev/fpga_led", O_WRONLY);
    write (fd, &wval, sizeof(wval));
    close(fd);

    return v;
}

JNIEXPORT jstring JNICALL Java_com_binus_dku_hanback_Handler_printMsg
        (JNIEnv *env, jclass c, jstring s){

    jboolean iscopy;
    int fd, ret;
    char *buf;

    fd = open("/dev/textlcd", O_WRONLY|O_NDELAY);

    initialize();

    buf = (char *)(*env).GetStringUTFChars(s, &iscopy);
    ioctl(fd, TEXTLCD_DD_ADDRESS, &strcommand, 32);
    ret = write(fd, buf, strlen(buf));

    close(fd);

    return s;

}

