require recipes-kernel/linux/linux-yocto.inc

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

LINUX_VERSION = "3.14"
KBRANCH = "vtss_${LINUX_VERSION}"
KMETA = ""

SRC_URI = "git://github.com/vtss/linux-stable.git;protocol=git;bareclone=1;branch=${KBRANCH}"
SRC_URI += "file://defconfig"

SRCREV_pn-linux-yocto ?= "${AUTOREV}"
SRCREV_machine        ?= "${AUTOREV}"

PR = "r1"
PV = "${LINUX_VERSION}+git${SRCPV}"

SRC_URI_append_luton26 =      " file://luton26.scc file://luton26.cfg"
SRC_URI_append_jaguar1 =      " file://jaguar1.scc file://jaguar1.cfg"
SRC_URI_append_jaguar1-dual = " file://jaguar1-dual.scc file://jaguar1-dual.cfg"
SRC_URI_append_serval1 =      " file://serval1.scc file://serval1.cfg"
SRC_URI_append_jaguar2 =      " file://jaguar2.scc file://jaguar2.cfg"
SRC_URI_append_serval2 =      " file://serval2.cfg"

COMPATIBLE_MACHINE = "luton26|jaguar1|jaguar1-dual|serval1|jaguar2|serval2"

KCONFIG_MODE="--alldefconfig"

kernel_do_deploy_append() {
    pwd
    ls -l
    ${OBJCOPY} -O binary -R .note -R .comment -S vmlinux vmlinux.bin
    gzip -9c < vmlinux.bin > ${DEPLOYDIR}/vmlinux-${LINUX_VERSION}.${MACHINE}.gz
    rm -f linux.bin
}
