#Rodrigo Munoz
#ID: 1001847694
#macos 13.3.1 (a) (22E772610a)
#This is perl 5, version 30, subversion 3 (v5.30.3) built for darwin-thread-multi-2level

#!/usr/bin/perl
use strict;
use warnings;

sub dirSize{
    my ($path) = @_;
    my $size = 0;

    #open the current working directory
    opendir(my $dir, $path) || die "Failed to open: $path\n";

    # iterate through files and directories
    while (my $file = readdir($dir)) {
        # check for current and prev dir
        next if ($file eq '.' || $file eq '..');
        my $newPath = "$path/$file";
        # if file
        if (-f $newPath) {
            $size += -s $newPath;
        }
        # if dir
        elsif (-d $newPath) {
            $size += dirSize($newPath);
        }
    }

    #clean up
    closedir($dir);
    return $size;
}

my $dirPath = '.';
my $size = dirSize($dirPath);

print "Dir size = $size\n";
