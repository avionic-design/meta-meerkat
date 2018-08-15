#@TYPE: COM
#@NAME: Avionic Design Meerkat COM
#@DESCRIPTION: Avionic Design Meerkat Computer on a Module

MACHINEOVERRIDES =. "meerkat:"

# Override the kernel
PREFERRED_PROVIDER_virtual/kernel ?= "linux-l4t-meerkat"

require conf/machine/include/tegra124.inc

# The COM has an integrated eMMC
MACHINE_FEATURES += "ext2 vfat"
# The SoC and the PMU have an RTC but the board has to provides backed
# up power for them to keep the time as is expected from an RTC.
MACHINE_FEATURES_BACKFILL_CONSIDERED += "rtc"

# Install the meerkat specific configs
MACHINE_ESSENTIAL_EXTRA_RDEPENDS += "meerkat-configs"
