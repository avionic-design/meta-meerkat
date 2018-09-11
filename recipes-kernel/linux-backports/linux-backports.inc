# Copyright (C) 2017 Khem Raj <raj.khem@gmail.com>
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "Linux Backports"
HOMEPAGE = "https://backports.wiki.kernel.org"
SECTION = "kernel/modules"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

inherit module cml1

SHRT_VER = "${@d.getVar('PV', True).split('-')[0]}"

SRC_URI = "http://www.kernel.org/pub/linux/kernel/projects/backports/stable/v${SHRT_VER}/backports-${PV}.tar.gz \
           file://backports_config \
"

S = "${WORKDIR}/backports-${PV}"

DEPENDS += "coreutils-native"

EXTRA_OEMAKE = "KLIB_BUILD=${STAGING_KERNEL_BUILDDIR} KLIB=${base_libdir}/modules/${KERNEL_VERSION}"
KCONFIG_CONFIG_COMMAND = "${EXTRA_OEMAKE} CC=${BUILD_CC} menuconfig"

MAKE_TARGETS = "modules"

do_configure_prepend() {
	# Make sure kconf is built to run on the host
	unset CFLAGS CPPFLAGS CXXFLAGS LDFLAGS
	oe_runmake -C kconf CC=${BUILD_CC} conf
	# Copy our config as a defconfig
	cp ${WORKDIR}/backports_config ${S}/defconfigs/yocto
	oe_runmake defconfig-yocto
}

# Create a meta package with another name, in this way we can
# always build this receipe but only install the modules
# when requiered.
KERNEL_MODULES_META_PACKAGE = "linux-backports-modules"

FILES_${KERNEL_MODULES_META_PACKAGE} = ""
ALLOW_EMPTY_${KERNEL_MODULES_META_PACKAGE} = "1"

PACKAGES += "${KERNEL_MODULES_META_PACKAGE}"

PACKAGES_DYNAMIC += "^${KERNEL_PACKAGE_NAME}-module-.*"
