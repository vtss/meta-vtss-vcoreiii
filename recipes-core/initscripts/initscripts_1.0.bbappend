FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

# Using _prepend ensures your path will be searched prior to other paths in the final list.
# So the revised populat_volatile.sh should replace the built-in version.'

PRINC := "${@int(PRINC) + 2}"