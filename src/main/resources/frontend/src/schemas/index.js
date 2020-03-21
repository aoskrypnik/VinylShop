import ComposerSchema from './definitions/composer'
import TrackSchema from './definitions/track'
import BandSchema from './definitions/band'
import ArtistSchema from './definitions/artist'
import Artist2TrackSchema from './definitions/artist2track'
import ParticipationSchema from './definitions/participation'
import AlbumSchema from './definitions/album'
import ReleaseSchema from './definitions/release'
import RecordSchema from './definitions/record'
import SalesmanSchema from './definitions/salesman'
import CustomerSchema from './definitions/customer'
import CheckSchema from './definitions/check'

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