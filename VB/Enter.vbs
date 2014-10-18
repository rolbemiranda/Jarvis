set shl = createobject("wscript.shell")
shl.AppActivate "tigerairways.com"
shl.sendkeys "{ENTER}"
set shl = nothing