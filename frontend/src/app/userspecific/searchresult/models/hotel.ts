export interface HotelCardDTO {
  hotelId: number;
  name: string;
  location: string;
  startingPrice?: number;  // number or null
  description?: string;
  imageUrl?: string;
}

export interface HotelCardSearchResultDTO  {
  byLocation: HotelCardDTO[];
  byName: HotelCardDTO[];
}
