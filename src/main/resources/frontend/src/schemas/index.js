import ComposerSchema from './composer'
import TrackSchema from './track'
import BandSchema from './band'
import ArtistSchema from './artist'
import Artist2TrackSchema from './artist2track'
import ParticipationSchema from './participation'

export default {
  composer: ComposerSchema,
  track: TrackSchema,
  artist: ArtistSchema,
  band: BandSchema,
  artist2track: Artist2TrackSchema,
  participation: ParticipationSchema
}