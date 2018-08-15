SECTION = "kernel"
SUMMARY = "Linux for Meerkat kernel recipe"
DESCRIPTION = "Linux kernel from sources provided by Avionic Design for Meerkat COM."
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

inherit kernel

PV .= "+git${SRCPV}"
FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}-${@bb.parse.BBHandler.vars_from_file(d.getVar('FILE', False),d)[1]}:"
EXTRA_OEMAKE += 'LIBGCC=""'

L4T_VERSION = "r21.6"
LOCALVERSION = "-meerkat-${L4T_VERSION}"

SRCBRANCH = "meerkat/l4t-r21-6"
SRCREV = "d343a56fcf511d219a7171299c1ec47870fcd179"
KERNEL_REPO = "github.com/avionic-design/linux-l4t"
SRC_URI = " \
    git://${KERNEL_REPO};branch=${SRCBRANCH} \
    file://defconfig \
"
S = "${WORKDIR}/git"

KERNEL_ROOTSPEC ?= "root=/dev/mmcblk\${devnum}p1 ro rootwait"

do_configure_prepend() {
    sed -e's,^CONFIG_LOCALVERSION=.*$,CONFIG_LOCALVERSION="${LOCALVERSION}",' < ${WORKDIR}/defconfig > ${B}/.config
    head=`git --git-dir=${S}/.git rev-parse --verify --short HEAD 2> /dev/null`
    printf "%s%s" "+g" $head > ${S}/.scmversion
}

KERNEL_ARGS_meerkat = "console=ttyS0,115200n8 console=tty1 rw rootwait gpt"

generate_extlinux_conf() {
    install -d ${D}/${KERNEL_IMAGEDEST}/extlinux
    rm -f ${D}/${KERNEL_IMAGEDEST}/extlinux/extlinux.conf
    cat >${D}/${KERNEL_IMAGEDEST}/extlinux/extlinux.conf << EOF
DEFAULT ${KERNEL_VERSION}
TIMEOUT 30
MENU TITLE Boot Options

LABEL ${KERNEL_VERSION}
	MENU LABEL ${KERNEL_VERSION}
	LINUX /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}-${KERNEL_VERSION}
	FDTDIR /${KERNEL_IMAGEDEST}
	APPEND ${KERNEL_ARGS} ${KERNEL_ROOTSPEC}
EOF
}

do_install[postfuncs] += "generate_extlinux_conf"

FILES_${KERNEL_PACKAGE_NAME}-image += "/${KERNEL_IMAGEDEST}/extlinux"

COMPATIBLE_MACHINE = "(meerkat$)"
