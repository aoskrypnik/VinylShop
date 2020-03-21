import ComposerSchema from './composer'
import TrackSchema from './track'
import BandSchema from './band'
import ArtistSchema from './artist'
import Artist2TrackSchema from './artist2track'
import ParticipationSchema from './participation'
import AlbumSchema from './album'
import ReleaseSchema from './release'
import RecordSchema from './record'
import SalesmanSchema from './salesman'
import CustomerSchema from './customer'
import CheckSchema from './check'

export default {
  composer: ComposerSchema,
  track: TrackSchema,
  artist: ArtistSchema,
  band: BandSchema,
  artist2track: Artist2TrackSchema,
  participation: ParticipationSchema,
  album: AlbumSchema,
  release: ReleaseSchema,
  record: RecordSchema,
  salesman: SalesmanSchema,
  customer: CustomerSchema,
  check: CheckSchema
}