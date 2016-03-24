##########################################
##                                      ##
##              Compile                 ##
##                                      ##
##   From the 'final' directory run:    ##
##                                      ##
##########################################

javac -cp "lib/testng.jar" @FilesList.txt -d bin

##########################################
##                                      ##
##            Run Program               ##
##                                      ##
##   From the 'final' directory run:    ##
##                                      ##
##########################################

java -cp ./bin com.teamc2.travellingsalesbee.TravellingSalesBee