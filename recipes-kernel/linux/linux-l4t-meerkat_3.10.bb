SECTION = "kernel"
SUMMARY = "Linux for Meerkat kernel recipe"
DESCRIPTION = "Linux kernel from sources provided by Avionic Design for Meerkat COM."
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

inherit kernel uboot-extlinux-config

PV .= "+git${SRCPV}"
FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}-${@bb.parse.BBHandler.vars_from_file(d.getVar('FILE', False),d)[1]}:"
EXTRA_OEMAKE += 'LIBGCC=""'

L4T_VERSION = "r21.7"
LOCALVERSION = "-meerkat-${L4T_VERSION}"

SRCBRANCH = "meerkat/l4t-r21-7"
SRCREV = "86bfa016325225a6817aa86a3290d2506cf8b918"
KERNEL_REPO = "github.com/avionic-design/linux-l4t"
SRC_URI = " \
    git://${KERNEL_REPO};branch=${SRCBRANCH} \
    file://defconfig \
"
S = "${WORKDIR}/git"

do_configure_prepend() {
    sed -e's,^CONFIG_LOCALVERSION=.*$,CONFIG_LOCALVERSION="${LOCALVERSION}",' < ${WORKDIR}/defconfig > ${B}/.config
    head=`git --git-dir=${S}/.git rev-parse --verify --short HEAD 2> /dev/null`
    printf "%s%s" "+g" $head > ${S}/.scmversion
}

UBOOT_EXTLINUX_CONSOLE ?= "console=ttyS0,115200n8 console=tty1"
UBOOT_EXTLINUX_ROOT ?= "root=/dev/mmcblk\${devnum}p1"
UBOOT_EXTLINUX_FDTDIR ?= "/${KERNEL_IMAGEDEST}"
UBOOT_EXTLINUX_KERNEL_IMAGE ?= "/${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}-${KERNEL_VERSION}"

UBOOT_EXTLINUX_INSTALL_DIR ?= "/${KERNEL_IMAGEDEST}/extlinux"
UBOOT_EXTLINUX_CONF_NAME ?= "extlinux.conf"
UBOOT_EXTLINUX ?= "1"

do_install_append() {
    if [ "${UBOOT_EXTLINUX}" = "1" ]
    then
        install -Dm 0644 ${UBOOT_EXTLINUX_CONFIG} ${D}/${UBOOT_EXTLINUX_INSTALL_DIR}/${UBOOT_EXTLINUX_CONF_NAME}
    fi
}

FILES_${KERNEL_PACKAGE_NAME}-image += "${UBOOT_EXTLINUX_INSTALL_DIR}/${UBOOT_EXTLINUX_CONF_NAME}"

COMPATIBLE_MACHINE = "(meerkat$)"

RRECOMMENDS_kernel += "linux-backports"
