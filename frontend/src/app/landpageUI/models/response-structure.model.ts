export interface ResponseStructure<T> {
  status: string;
  message: string;
  data: T | null;
}
