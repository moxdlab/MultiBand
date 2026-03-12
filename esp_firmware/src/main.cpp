#include <Arduino.h>
#include <WiFi.h>
#include <WiFiUdp.h>

#define MAX_TOUCHES 5

#include "TouchSensor.h"

const char *ssid = "MultiBand-AP";
const char *password = "12345678";
const char *udpAddress = "192.168.4.2";
const int udpPort = 44444;

WiFiUDP udp;

boolean touchActive = false;
MultitouchSensor multitouchSensor;

int64_t lastMessageSent, timeSinceBoot;
int packetsPerSecond = 121;

void setupWiFi();
void printSensorData(std::array<Touch, MAX_TOUCHES> touches);
void sendDataViaUDP(std::array<Touch, MAX_TOUCHES> touches);

void setup() {
    Serial.begin(115200);
    setupWiFi();
    delay(200);
    Serial.println("Setting up Trill Flex");
    multitouchSensor.setup(4);
    delay(50);
    timeSinceBoot = esp_timer_get_time();
    lastMessageSent = 0;
}

void setupWiFi() {
    Serial.println("Setup WiFi");
    Serial.println("\n[*] Creating AP");
    WiFiClass::mode(WIFI_AP);
    WiFi.softAP(ssid, password, 1, 0, 2);
    Serial.print("[+] AP Created with IP Gateway ");
    Serial.println(WiFi.softAPIP());
}

void loop() {
    timeSinceBoot = esp_timer_get_time();
    if ((timeSinceBoot - lastMessageSent) >= 1000000 / packetsPerSecond) {
        multitouchSensor.read_data();
        std::array<Touch, MAX_TOUCHES> touches = multitouchSensor.get_touches_from_data();

        //printSensorData(touches);
        if (multitouchSensor.get_num_touches() != 0) {
            touchActive = true;
            sendDataViaUDP(touches);
        } else if (touchActive) {
            touchActive = false;
            sendDataViaUDP(touches);
        }
    }
}

void sendDataViaUDP(std::array<Touch, MAX_TOUCHES> touches) {
    int arr[multitouchSensor.get_num_touches() + 1];
    for (int n = 0; n < multitouchSensor.get_num_touches(); n++) {
        arr[n] = (int) touches[n].get_position()*100;
    }
    arr[multitouchSensor.get_num_touches()] = -1;

    udp.beginPacket(udpAddress, udpPort);
    udp.write((const uint8_t*)arr, sizeof(arr));
    udp.endPacket();
    lastMessageSent = esp_timer_get_time();
}

void printSensorData(std::array<Touch, MAX_TOUCHES> touches) {
    for (int i = 0; i < multitouchSensor.get_num_touches(); i++) {
        Touch curTouch = touches[i];
        Serial.print(i);
        Serial.print(".Location: ");
        Serial.print(curTouch.get_position());
        Serial.print(", Size: ");
        Serial.print(curTouch.get_pressure());
        Serial.println("");
    }
}