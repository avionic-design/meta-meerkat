SUMMARY = "Config files for the Meerkat SOM"
SECTION = "base"

LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"

SRC_URI += " \
    file://meerkat-temperature-sensor.rules \
    file://thermal2hwmon \
"

S = "${WORKDIR}"

do_install() {
    install -d ${D}${sbindir}
    install -m 0755 ${S}/thermal2hwmon ${D}${sbindir}/thermal2hwmon
    install -d ${D}${sysconfdir}/udev/rules.d
    install -m 0644 ${S}/meerkat-temperature-sensor.rules ${D}${sysconfdir}/udev/rules.d/meerkat-temperature-sensor.rules
}

COMPATIBLE_MACHINE = "(meerkat$)"
