README.txt
Authors: Aman Panda, Noah Brackenbury, Abha Laddha, Micah Nacht,

To be able to create music is a wonderful thing, and our Beatpad allows you to do just that. No matter if you're a beginner or a musical expert, Beatpad makes it easy to map out simple beats and melodic ideas. The app can be split into 4 sections. On the right, we have a sidebar that contains simple instructions for the user, a volume slider, a metronome, a choice of presets, and a button that clears all looped sound files. To the left of the sidebar, on top, we have 6 sample buttons followed by 9 loop buttons. Notice how the respective buttons are not labeled. We made this design choice for the following two reasons: First, we wanted to mimic real life beat pads that tend to have very minimalistic designs; and second, because we wish for users to tinker around with buttons and to hear what they do and be less influenced by what they see! On the bottom, we have our melody maker, which allows users to map out a measure of a melody in the C-major scale, with access to 16th note placements.

Clearly, the upper looping and sampling buttons require very little proficiency with music to use and create music with. While the bottom melody maker would certainly benefit from a knowledge in music, beginners can also make use of it's functionality and map out a melody that will very easily sound good -- because all notes are in the c-major scale, users will be unable to choose dissonant notes.  

A bug to consider with the app is the case in which a user selects too many sound files to be looped on a given beat. The latency associated with triggering sound files makes it such that if too many sound files have been scheduled to start at a given beat, then starting them sequentially once that beat is reached can potentially put things out of sync. We were unable to figure out how to trigger sound files in parallel, and this is certainly the first fix we would make if there was more time.

If we had more time to work on the project, we would have implemented the following features:
	1. A way to change tempo (Right now, it is fixed at 60 BPM)
	2. Access to different key centers (Right now, melody maker is fixed in the C-major scale)
	3. Access to Multiple soundbanks - possibly one with sound files created by the app creators themselves!
	4. A way for users to record and save created loops.
	5. A way for users to load in their own sound files into the 9 looper buttons.



Loops:
1 - kick
2 - hi-hat offbeat
3 - hi-hat lick
4 - snare
5 - toms
6 - rimshot
7 - 808 kick
8 - ting
9 - clap

Samples:
1 - crash
2 - cymbal roll
3 - "uh"
4 - "be a hit"
5 - fresh
6 - elevator