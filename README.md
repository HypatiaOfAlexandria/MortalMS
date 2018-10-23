# MortalMS

[![License](https://img.shields.io/badge/license-AGPL%20v3%2B-brightgreen.svg)](https://www.gnu.org/licenses/agpl-3.0.html)
[![Java standard](https://img.shields.io/badge/Java-10+-blue.svg)](https://en.wikipedia.org/wiki/Java_version_history#Java_SE_10)
[![Maplestory version](https://img.shields.io/badge/Maplestory-GMS_v83-orange.svg)](https://en.wikipedia.org/wiki/MapleStory)

A fork of [HeavenMS](https://github.com/ronancpl/HeavenMS), originally
developed by Ronan C. P. Lana for "MapleSolaxia". Furthermore a modification of
the *original*, OdinMS, as developed by the OdinMS team so long ago (bless
their souls).

## Legal

Getting the legal stuff out of the way, this code is all licensed under
[the GNU AGPL v3+](https://www.gnu.org/licenses/agpl-3.0.en.html). So it's all
free/libre/open source software and all that jazz. You are, as usual, free to
install, use, modify, and redistribute this stuff as you see fit (for any
purpose) so long as any derivative works are also licensed under the AGPL v3+
and you retain credit to any original author(s). The original HeavenMS README
contained the following text:

> ...it is meant that anyone is **free to install, use, modify and**
> **redistribute the contents**, as long as there is **no kind of commercial**
> **trading involved** and the **credits to the original creators are**
> **maintained** within the codes.

(Emphasis in the original.)

This is basically wrong, mostly the part about "as long as there is no kind of
commercial trading involved". You **definitely can** use any of this for
commercial purposes; this follows directly from the basic freedom of free
software that you may use/run/distribute the software (or modified versions of
the software) for **any purpose**.

Furthermore &mdash; since this is the AGPL &mdash; running code on behalf of
user(s) on a machine that you (yes, you the server owner, *not* the user)
control still counts as the user running the code for their own purposes.
**You running this server for others to play on counts as redistribution for**
**the purposes of software open sourcing and licensing!** Yes, that means that
you must offer a copy of the exact source code that you use to prepare the
program that you run on your server for all of your users, and offer it
publicly in accordance with the AGPL v3+.

I can already hear you moaning: "but no one else open sources their server
code, and I'm sure they all use OdinMS derivatives too!" That's an astute
observation. Unfortunately the only reason that this is the case is because of
a combination of greed, hubris, and ignorance. What all of those servers do is
in fact **illegal** (and **actionably so**, there is case law for suits against
noncomplicance with GNU (A)GPL licenses).

Don't believe me? Here, let's have a look at the text for ourselves. The GNU
Affero General Public License version 3 (AGPL v3) contains the following
passage within section 13:

> ...if you modify the Program, your modified version must prominently offer
> all users interacting with it remotely through a computer network (if your
> version supports such interaction) an opportunity to receive the
> Corresponding Source of your version...

Yes, that's right. **All users interacting with it remotely.** Since all
versions of this software count as modified versions of OdinMS, they all fall
under the purview of this clause. Even if you could get something that is
*arguably* unmodified, you would still be out of luck because the original
(presumably the one in this repo) would still be public anyways. And that's
highly unlikely since anyone using it for their own purpose is going to be
making at least some minor modifications. Sorry, there's no way around it, you
will just have to be a decent person about it. Tough.

## Client & Data

This repo contains the server-side nonsense, but the client and the raw \*.nx
data are another story.

* Client: JourneyClient
* NX data: TODO

## Development Status

Probably not active (who knows).

## Disclaimers

This server source is **not intended to be stable** as-is. Security audits/pen
testing/stress testing, proper deadlock review, and other maintenance checks
are needed in order to make it suitable for production use.

> THERE IS NO WARRANTY FOR THE PROGRAM, TO THE EXTENT PERMITTED BY APPLICABLE
> LAW. EXCEPT WHEN OTHERWISE STATED IN WRITING THE COPYRIGHT HOLDERS AND/OR
> OTHER PARTIES PROVIDE THE PROGRAM "AS IS" WITHOUT WARRANTY OF ANY KIND,
> EITHER EXPRESSED OR IMPLIED, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
> WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE. THE
> ENTIRE RISK AS TO THE QUALITY AND PERFORMANCE OF THE PROGRAM IS WITH YOU.
> SHOULD THE PROGRAM PROVE DEFECTIVE, YOU ASSUME THE COST OF ALL NECESSARY
> SERVICING, REPAIR OR CORRECTION.

## Setup

The "posix-compile.sh" and "posix-launch.sh" files assume that the programs
named "java" and "javac" in your path refer to the OpenJDK implementations of
the Java virtual machine version 10+, and the Java compiler version 10+,
respectively.

Run `./posix-compile.sh` to compile the server code.

Start up MariaDB and execute the provided "db_database.sql" and "db_drops.sql"
scripts, **in that order**. (Hint: in the MariaDB/MySQL command line, that
looks something like: `source /the/full/path/to/your/db_database.sql;`)
Optionally, you can also then execute "db_shopupdate.sql", although this script
is only provided as-is from the original HeavenMS and is not expected to be
useful.

At the end of the execution of the SQL scripts, you should have installed a
database schema called "heavenms". Register your first account to be used
in-game by **manually creating** an entry in the table "accounts", with a name
and a password.

Create your own blank configuration template by copying
"configuration.ini.template" to "configuration.ini". Configure the IP you want
to use for your MapleStory server in your own "configuration.ini" file, or set
it as "localhost" if you want to run it only on your machine. Also make sure
that the username and password for your MariaDB login are correct.

You should now be able to start the server by executing `./posix-launch.sh`.
