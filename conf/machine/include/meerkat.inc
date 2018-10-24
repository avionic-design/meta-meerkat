#@TYPE: COM
#@NAME: Avionic Design Meerkat COM
#@DESCRIPTION: Avionic Design Meerkat Computer on a Module

MACHINEOVERRIDES =. "meerkat:"

# Override the kernel
PREFERRED_PROVIDER_virtual/kernel ?= "linux-l4t-meerkat"

# Parameters to build a valid uImage or fitImage
UBOOT_LOADADDRESS = "0x82000000"
ENTRYPOINT = "0x82000000"

require conf/machine/include/tegra124.inc

# The COM has an integrated eMMC
MACHINE_FEATURES += "ext2 vfat"
# The SoC and the PMU have an RTC but the board has to provides backed
# up power for them to keep the time as is expected from an RTC.
MACHINE_FEATURES_BACKFILL_CONSIDERED += "rtc"

# Install the meerkat specific configs
MACHINE_ESSENTIAL_EXTRA_RDEPENDS += "meerkat-configs"

# Add a package arch for all Meerkat based machines
COM_PKGARCH = "${SOC_FAMILY_PKGARCH}_meerkat"
PACKAGE_EXTRA_ARCHS_append = " ${COM_PKGARCH}"
