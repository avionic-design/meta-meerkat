include linux-backports.inc

SRC_URI += " \
    file://0001-Disable-the-depmod-config-changes-call-to-depmod-and.patch \
    file://0002-cred.h-Fix-the-redefinition-of-current_user_ns-on-ne.patch \
    file://0003-crypto-ccm-crypto_memneq-has-been-backported-to-3.12.patch \
    file://0004-crypto-ccm-crypto_aead_set_reqs-is-only-available-si.patch \
    file://0005-Remove-functions-that-we-already-backported-to-the-m.patch \
    file://0006-ath10k-disable-PCI-PS-for-QCA988X-and-QCA99X0.patch \
"

SRC_URI[md5sum] = "72aca26f15d93bdda02a7c84cb9bb0be"
SRC_URI[sha256sum] = "ecc7167b0e5e6f846ed1cc3f9c58e4a2f43719434fe4cc57bb12165c9bbb9d46"
