export function filter(hue: number, brightness: number, saturation: number) {
    return `hue-rotate(${hue}deg) brightness(${brightness}%) saturate(${saturation}%)`;
}
